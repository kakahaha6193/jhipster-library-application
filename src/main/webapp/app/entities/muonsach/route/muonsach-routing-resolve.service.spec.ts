jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMuonsach, Muonsach } from '../muonsach.model';
import { MuonsachService } from '../service/muonsach.service';

import { MuonsachRoutingResolveService } from './muonsach-routing-resolve.service';

describe('Service Tests', () => {
  describe('Muonsach routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: MuonsachRoutingResolveService;
    let service: MuonsachService;
    let resultMuonsach: IMuonsach | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(MuonsachRoutingResolveService);
      service = TestBed.inject(MuonsachService);
      resultMuonsach = undefined;
    });

    describe('resolve', () => {
      it('should return IMuonsach returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMuonsach = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMuonsach).toEqual({ id: 123 });
      });

      it('should return new IMuonsach if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMuonsach = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultMuonsach).toEqual(new Muonsach());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMuonsach = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMuonsach).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
