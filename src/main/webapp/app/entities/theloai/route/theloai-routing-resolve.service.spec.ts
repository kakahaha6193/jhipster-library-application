jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITheloai, Theloai } from '../theloai.model';
import { TheloaiService } from '../service/theloai.service';

import { TheloaiRoutingResolveService } from './theloai-routing-resolve.service';

describe('Service Tests', () => {
  describe('Theloai routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TheloaiRoutingResolveService;
    let service: TheloaiService;
    let resultTheloai: ITheloai | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TheloaiRoutingResolveService);
      service = TestBed.inject(TheloaiService);
      resultTheloai = undefined;
    });

    describe('resolve', () => {
      it('should return ITheloai returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTheloai = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTheloai).toEqual({ id: 123 });
      });

      it('should return new ITheloai if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTheloai = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTheloai).toEqual(new Theloai());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTheloai = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTheloai).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
