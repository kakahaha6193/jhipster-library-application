jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDocgia, Docgia } from '../docgia.model';
import { DocgiaService } from '../service/docgia.service';

import { DocgiaRoutingResolveService } from './docgia-routing-resolve.service';

describe('Service Tests', () => {
  describe('Docgia routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DocgiaRoutingResolveService;
    let service: DocgiaService;
    let resultDocgia: IDocgia | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DocgiaRoutingResolveService);
      service = TestBed.inject(DocgiaService);
      resultDocgia = undefined;
    });

    describe('resolve', () => {
      it('should return IDocgia returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDocgia = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDocgia).toEqual({ id: 123 });
      });

      it('should return new IDocgia if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDocgia = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDocgia).toEqual(new Docgia());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDocgia = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDocgia).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
