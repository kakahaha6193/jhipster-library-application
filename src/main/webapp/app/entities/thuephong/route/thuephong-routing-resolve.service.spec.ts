jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IThuephong, Thuephong } from '../thuephong.model';
import { ThuephongService } from '../service/thuephong.service';

import { ThuephongRoutingResolveService } from './thuephong-routing-resolve.service';

describe('Service Tests', () => {
  describe('Thuephong routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ThuephongRoutingResolveService;
    let service: ThuephongService;
    let resultThuephong: IThuephong | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ThuephongRoutingResolveService);
      service = TestBed.inject(ThuephongService);
      resultThuephong = undefined;
    });

    describe('resolve', () => {
      it('should return IThuephong returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultThuephong = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultThuephong).toEqual({ id: 123 });
      });

      it('should return new IThuephong if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultThuephong = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultThuephong).toEqual(new Thuephong());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultThuephong = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultThuephong).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
