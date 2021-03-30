import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INhaxuatban, Nhaxuatban } from '../nhaxuatban.model';

import { NhaxuatbanService } from './nhaxuatban.service';

describe('Service Tests', () => {
  describe('Nhaxuatban Service', () => {
    let service: NhaxuatbanService;
    let httpMock: HttpTestingController;
    let elemDefault: INhaxuatban;
    let expectedResult: INhaxuatban | INhaxuatban[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(NhaxuatbanService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tenNXB: 'AAAAAAA',
        diaChi: 'AAAAAAA',
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

      it('should create a Nhaxuatban', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Nhaxuatban()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Nhaxuatban', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenNXB: 'BBBBBB',
            diaChi: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Nhaxuatban', () => {
        const patchObject = Object.assign({}, new Nhaxuatban());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Nhaxuatban', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenNXB: 'BBBBBB',
            diaChi: 'BBBBBB',
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

      it('should delete a Nhaxuatban', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addNhaxuatbanToCollectionIfMissing', () => {
        it('should add a Nhaxuatban to an empty array', () => {
          const nhaxuatban: INhaxuatban = { id: 123 };
          expectedResult = service.addNhaxuatbanToCollectionIfMissing([], nhaxuatban);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nhaxuatban);
        });

        it('should not add a Nhaxuatban to an array that contains it', () => {
          const nhaxuatban: INhaxuatban = { id: 123 };
          const nhaxuatbanCollection: INhaxuatban[] = [
            {
              ...nhaxuatban,
            },
            { id: 456 },
          ];
          expectedResult = service.addNhaxuatbanToCollectionIfMissing(nhaxuatbanCollection, nhaxuatban);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Nhaxuatban to an array that doesn't contain it", () => {
          const nhaxuatban: INhaxuatban = { id: 123 };
          const nhaxuatbanCollection: INhaxuatban[] = [{ id: 456 }];
          expectedResult = service.addNhaxuatbanToCollectionIfMissing(nhaxuatbanCollection, nhaxuatban);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nhaxuatban);
        });

        it('should add only unique Nhaxuatban to an array', () => {
          const nhaxuatbanArray: INhaxuatban[] = [{ id: 123 }, { id: 456 }, { id: 38073 }];
          const nhaxuatbanCollection: INhaxuatban[] = [{ id: 123 }];
          expectedResult = service.addNhaxuatbanToCollectionIfMissing(nhaxuatbanCollection, ...nhaxuatbanArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const nhaxuatban: INhaxuatban = { id: 123 };
          const nhaxuatban2: INhaxuatban = { id: 456 };
          expectedResult = service.addNhaxuatbanToCollectionIfMissing([], nhaxuatban, nhaxuatban2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nhaxuatban);
          expect(expectedResult).toContain(nhaxuatban2);
        });

        it('should accept null and undefined values', () => {
          const nhaxuatban: INhaxuatban = { id: 123 };
          expectedResult = service.addNhaxuatbanToCollectionIfMissing([], null, nhaxuatban, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nhaxuatban);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
