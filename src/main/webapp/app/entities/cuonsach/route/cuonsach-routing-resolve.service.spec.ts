jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICuonsach, Cuonsach } from '../cuonsach.model';
import { CuonsachService } from '../service/cuonsach.service';

import { CuonsachRoutingResolveService } from './cuonsach-routing-resolve.service';

describe('Service Tests', () => {
  describe('Cuonsach routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CuonsachRoutingResolveService;
    let service: CuonsachService;
    let resultCuonsach: ICuonsach | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CuonsachRoutingResolveService);
      service = TestBed.inject(CuonsachService);
      resultCuonsach = undefined;
    });

    describe('resolve', () => {
      it('should return ICuonsach returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCuonsach = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCuonsach).toEqual({ id: 123 });
      });

      it('should return new ICuonsach if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCuonsach = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCuonsach).toEqual(new Cuonsach());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCuonsach = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCuonsach).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
