import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPhongdocsach, Phongdocsach } from '../phongdocsach.model';

import { PhongdocsachService } from './phongdocsach.service';

describe('Service Tests', () => {
  describe('Phongdocsach Service', () => {
    let service: PhongdocsachService;
    let httpMock: HttpTestingController;
    let elemDefault: IPhongdocsach;
    let expectedResult: IPhongdocsach | IPhongdocsach[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PhongdocsachService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tenPhong: 'AAAAAAA',
        viTri: 'AAAAAAA',
        sucChua: 0,
        giaThue: 0,
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

      it('should create a Phongdocsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Phongdocsach()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Phongdocsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenPhong: 'BBBBBB',
            viTri: 'BBBBBB',
            sucChua: 1,
            giaThue: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Phongdocsach', () => {
        const patchObject = Object.assign(
          {
            sucChua: 1,
            giaThue: 1,
          },
          new Phongdocsach()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Phongdocsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenPhong: 'BBBBBB',
            viTri: 'BBBBBB',
            sucChua: 1,
            giaThue: 1,
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

      it('should delete a Phongdocsach', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPhongdocsachToCollectionIfMissing', () => {
        it('should add a Phongdocsach to an empty array', () => {
          const phongdocsach: IPhongdocsach = { id: 123 };
          expectedResult = service.addPhongdocsachToCollectionIfMissing([], phongdocsach);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(phongdocsach);
        });

        it('should not add a Phongdocsach to an array that contains it', () => {
          const phongdocsach: IPhongdocsach = { id: 123 };
          const phongdocsachCollection: IPhongdocsach[] = [
            {
              ...phongdocsach,
            },
            { id: 456 },
          ];
          expectedResult = service.addPhongdocsachToCollectionIfMissing(phongdocsachCollection, phongdocsach);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Phongdocsach to an array that doesn't contain it", () => {
          const phongdocsach: IPhongdocsach = { id: 123 };
          const phongdocsachCollection: IPhongdocsach[] = [{ id: 456 }];
          expectedResult = service.addPhongdocsachToCollectionIfMissing(phongdocsachCollection, phongdocsach);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(phongdocsach);
        });

        it('should add only unique Phongdocsach to an array', () => {
          const phongdocsachArray: IPhongdocsach[] = [{ id: 123 }, { id: 456 }, { id: 63922 }];
          const phongdocsachCollection: IPhongdocsach[] = [{ id: 123 }];
          expectedResult = service.addPhongdocsachToCollectionIfMissing(phongdocsachCollection, ...phongdocsachArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const phongdocsach: IPhongdocsach = { id: 123 };
          const phongdocsach2: IPhongdocsach = { id: 456 };
          expectedResult = service.addPhongdocsachToCollectionIfMissing([], phongdocsach, phongdocsach2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(phongdocsach);
          expect(expectedResult).toContain(phongdocsach2);
        });

        it('should accept null and undefined values', () => {
          const phongdocsach: IPhongdocsach = { id: 123 };
          expectedResult = service.addPhongdocsachToCollectionIfMissing([], null, phongdocsach, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(phongdocsach);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
