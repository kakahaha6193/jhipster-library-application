import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPhongdungsach, Phongdungsach } from '../phongdungsach.model';

import { PhongdungsachService } from './phongdungsach.service';

describe('Service Tests', () => {
  describe('Phongdungsach Service', () => {
    let service: PhongdungsachService;
    let httpMock: HttpTestingController;
    let elemDefault: IPhongdungsach;
    let expectedResult: IPhongdungsach | IPhongdungsach[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PhongdungsachService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tenPhong: 'AAAAAAA',
        viTri: 'AAAAAAA',
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

      it('should create a Phongdungsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Phongdungsach()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Phongdungsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenPhong: 'BBBBBB',
            viTri: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Phongdungsach', () => {
        const patchObject = Object.assign(
          {
            tenPhong: 'BBBBBB',
            viTri: 'BBBBBB',
          },
          new Phongdungsach()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Phongdungsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenPhong: 'BBBBBB',
            viTri: 'BBBBBB',
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

      it('should delete a Phongdungsach', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPhongdungsachToCollectionIfMissing', () => {
        it('should add a Phongdungsach to an empty array', () => {
          const phongdungsach: IPhongdungsach = { id: 123 };
          expectedResult = service.addPhongdungsachToCollectionIfMissing([], phongdungsach);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(phongdungsach);
        });

        it('should not add a Phongdungsach to an array that contains it', () => {
          const phongdungsach: IPhongdungsach = { id: 123 };
          const phongdungsachCollection: IPhongdungsach[] = [
            {
              ...phongdungsach,
            },
            { id: 456 },
          ];
          expectedResult = service.addPhongdungsachToCollectionIfMissing(phongdungsachCollection, phongdungsach);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Phongdungsach to an array that doesn't contain it", () => {
          const phongdungsach: IPhongdungsach = { id: 123 };
          const phongdungsachCollection: IPhongdungsach[] = [{ id: 456 }];
          expectedResult = service.addPhongdungsachToCollectionIfMissing(phongdungsachCollection, phongdungsach);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(phongdungsach);
        });

        it('should add only unique Phongdungsach to an array', () => {
          const phongdungsachArray: IPhongdungsach[] = [{ id: 123 }, { id: 456 }, { id: 13265 }];
          const phongdungsachCollection: IPhongdungsach[] = [{ id: 123 }];
          expectedResult = service.addPhongdungsachToCollectionIfMissing(phongdungsachCollection, ...phongdungsachArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const phongdungsach: IPhongdungsach = { id: 123 };
          const phongdungsach2: IPhongdungsach = { id: 456 };
          expectedResult = service.addPhongdungsachToCollectionIfMissing([], phongdungsach, phongdungsach2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(phongdungsach);
          expect(expectedResult).toContain(phongdungsach2);
        });

        it('should accept null and undefined values', () => {
          const phongdungsach: IPhongdungsach = { id: 123 };
          expectedResult = service.addPhongdungsachToCollectionIfMissing([], null, phongdungsach, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(phongdungsach);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
