import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGiasach, Giasach } from '../giasach.model';

import { GiasachService } from './giasach.service';

describe('Service Tests', () => {
  describe('Giasach Service', () => {
    let service: GiasachService;
    let httpMock: HttpTestingController;
    let elemDefault: IGiasach;
    let expectedResult: IGiasach | IGiasach[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(GiasachService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        thuTu: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Giasach', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Giasach()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Giasach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            thuTu: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Giasach', () => {
        const patchObject = Object.assign({}, new Giasach());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Giasach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            thuTu: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Giasach', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addGiasachToCollectionIfMissing', () => {
        it('should add a Giasach to an empty array', () => {
          const giasach: IGiasach = { id: 123 };
          expectedResult = service.addGiasachToCollectionIfMissing([], giasach);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(giasach);
        });

        it('should not add a Giasach to an array that contains it', () => {
          const giasach: IGiasach = { id: 123 };
          const giasachCollection: IGiasach[] = [
            {
              ...giasach,
            },
            { id: 456 },
          ];
          expectedResult = service.addGiasachToCollectionIfMissing(giasachCollection, giasach);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Giasach to an array that doesn't contain it", () => {
          const giasach: IGiasach = { id: 123 };
          const giasachCollection: IGiasach[] = [{ id: 456 }];
          expectedResult = service.addGiasachToCollectionIfMissing(giasachCollection, giasach);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(giasach);
        });

        it('should add only unique Giasach to an array', () => {
          const giasachArray: IGiasach[] = [{ id: 123 }, { id: 456 }, { id: 61288 }];
          const giasachCollection: IGiasach[] = [{ id: 123 }];
          expectedResult = service.addGiasachToCollectionIfMissing(giasachCollection, ...giasachArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const giasach: IGiasach = { id: 123 };
          const giasach2: IGiasach = { id: 456 };
          expectedResult = service.addGiasachToCollectionIfMissing([], giasach, giasach2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(giasach);
          expect(expectedResult).toContain(giasach2);
        });

        it('should accept null and undefined values', () => {
          const giasach: IGiasach = { id: 123 };
          expectedResult = service.addGiasachToCollectionIfMissing([], null, giasach, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(giasach);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
