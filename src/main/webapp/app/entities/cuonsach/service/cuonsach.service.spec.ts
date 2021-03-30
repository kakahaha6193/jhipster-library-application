import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICuonsach, Cuonsach } from '../cuonsach.model';

import { CuonsachService } from './cuonsach.service';

describe('Service Tests', () => {
  describe('Cuonsach Service', () => {
    let service: CuonsachService;
    let httpMock: HttpTestingController;
    let elemDefault: ICuonsach;
    let expectedResult: ICuonsach | ICuonsach[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CuonsachService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        ngayHetHan: currentDate,
        trangThai: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            ngayHetHan: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Cuonsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            ngayHetHan: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayHetHan: currentDate,
          },
          returnedFromService
        );

        service.create(new Cuonsach()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Cuonsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            ngayHetHan: currentDate.format(DATE_TIME_FORMAT),
            trangThai: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayHetHan: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Cuonsach', () => {
        const patchObject = Object.assign(
          {
            ngayHetHan: currentDate.format(DATE_TIME_FORMAT),
            trangThai: 1,
          },
          new Cuonsach()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            ngayHetHan: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Cuonsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            ngayHetHan: currentDate.format(DATE_TIME_FORMAT),
            trangThai: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayHetHan: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Cuonsach', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCuonsachToCollectionIfMissing', () => {
        it('should add a Cuonsach to an empty array', () => {
          const cuonsach: ICuonsach = { id: 123 };
          expectedResult = service.addCuonsachToCollectionIfMissing([], cuonsach);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cuonsach);
        });

        it('should not add a Cuonsach to an array that contains it', () => {
          const cuonsach: ICuonsach = { id: 123 };
          const cuonsachCollection: ICuonsach[] = [
            {
              ...cuonsach,
            },
            { id: 456 },
          ];
          expectedResult = service.addCuonsachToCollectionIfMissing(cuonsachCollection, cuonsach);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Cuonsach to an array that doesn't contain it", () => {
          const cuonsach: ICuonsach = { id: 123 };
          const cuonsachCollection: ICuonsach[] = [{ id: 456 }];
          expectedResult = service.addCuonsachToCollectionIfMissing(cuonsachCollection, cuonsach);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cuonsach);
        });

        it('should add only unique Cuonsach to an array', () => {
          const cuonsachArray: ICuonsach[] = [{ id: 123 }, { id: 456 }, { id: 97165 }];
          const cuonsachCollection: ICuonsach[] = [{ id: 123 }];
          expectedResult = service.addCuonsachToCollectionIfMissing(cuonsachCollection, ...cuonsachArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const cuonsach: ICuonsach = { id: 123 };
          const cuonsach2: ICuonsach = { id: 456 };
          expectedResult = service.addCuonsachToCollectionIfMissing([], cuonsach, cuonsach2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cuonsach);
          expect(expectedResult).toContain(cuonsach2);
        });

        it('should accept null and undefined values', () => {
          const cuonsach: ICuonsach = { id: 123 };
          expectedResult = service.addCuonsachToCollectionIfMissing([], null, cuonsach, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cuonsach);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
