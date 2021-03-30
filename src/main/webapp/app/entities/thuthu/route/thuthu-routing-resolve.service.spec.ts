jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IThuthu, Thuthu } from '../thuthu.model';
import { ThuthuService } from '../service/thuthu.service';

import { ThuthuRoutingResolveService } from './thuthu-routing-resolve.service';

describe('Service Tests', () => {
  describe('Thuthu routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ThuthuRoutingResolveService;
    let service: ThuthuService;
    let resultThuthu: IThuthu | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ThuthuRoutingResolveService);
      service = TestBed.inject(ThuthuService);
      resultThuthu = undefined;
    });

    describe('resolve', () => {
      it('should return IThuthu returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultThuthu = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultThuthu).toEqual({ id: 123 });
      });

      it('should return new IThuthu if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultThuthu = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultThuthu).toEqual(new Thuthu());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultThuthu = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultThuthu).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
