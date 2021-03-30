jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IGiasach, Giasach } from '../giasach.model';
import { GiasachService } from '../service/giasach.service';

import { GiasachRoutingResolveService } from './giasach-routing-resolve.service';

describe('Service Tests', () => {
  describe('Giasach routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: GiasachRoutingResolveService;
    let service: GiasachService;
    let resultGiasach: IGiasach | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(GiasachRoutingResolveService);
      service = TestBed.inject(GiasachService);
      resultGiasach = undefined;
    });

    describe('resolve', () => {
      it('should return IGiasach returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGiasach = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGiasach).toEqual({ id: 123 });
      });

      it('should return new IGiasach if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGiasach = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultGiasach).toEqual(new Giasach());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGiasach = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGiasach).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
