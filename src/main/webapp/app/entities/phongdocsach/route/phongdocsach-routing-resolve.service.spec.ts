jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPhongdocsach, Phongdocsach } from '../phongdocsach.model';
import { PhongdocsachService } from '../service/phongdocsach.service';

import { PhongdocsachRoutingResolveService } from './phongdocsach-routing-resolve.service';

describe('Service Tests', () => {
  describe('Phongdocsach routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PhongdocsachRoutingResolveService;
    let service: PhongdocsachService;
    let resultPhongdocsach: IPhongdocsach | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PhongdocsachRoutingResolveService);
      service = TestBed.inject(PhongdocsachService);
      resultPhongdocsach = undefined;
    });

    describe('resolve', () => {
      it('should return IPhongdocsach returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPhongdocsach = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPhongdocsach).toEqual({ id: 123 });
      });

      it('should return new IPhongdocsach if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPhongdocsach = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPhongdocsach).toEqual(new Phongdocsach());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPhongdocsach = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPhongdocsach).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
