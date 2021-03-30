import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INhapsach, Nhapsach } from '../nhapsach.model';

import { NhapsachService } from './nhapsach.service';

describe('Service Tests', () => {
  describe('Nhapsach Service', () => {
    let service: NhapsachService;
    let httpMock: HttpTestingController;
    let elemDefault: INhapsach;
    let expectedResult: INhapsach | INhapsach[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(NhapsachService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        ngayGioNhap: currentDate,
        soLuong: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            ngayGioNhap: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Nhapsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            ngayGioNhap: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayGioNhap: currentDate,
          },
          returnedFromService
        );

        service.create(new Nhapsach()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Nhapsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            ngayGioNhap: currentDate.format(DATE_TIME_FORMAT),
            soLuong: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayGioNhap: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Nhapsach', () => {
        const patchObject = Object.assign({}, new Nhapsach());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            ngayGioNhap: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Nhapsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            ngayGioNhap: currentDate.format(DATE_TIME_FORMAT),
            soLuong: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayGioNhap: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Nhapsach', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addNhapsachToCollectionIfMissing', () => {
        it('should add a Nhapsach to an empty array', () => {
          const nhapsach: INhapsach = { id: 123 };
          expectedResult = service.addNhapsachToCollectionIfMissing([], nhapsach);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nhapsach);
        });

        it('should not add a Nhapsach to an array that contains it', () => {
          const nhapsach: INhapsach = { id: 123 };
          const nhapsachCollection: INhapsach[] = [
            {
              ...nhapsach,
            },
            { id: 456 },
          ];
          expectedResult = service.addNhapsachToCollectionIfMissing(nhapsachCollection, nhapsach);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Nhapsach to an array that doesn't contain it", () => {
          const nhapsach: INhapsach = { id: 123 };
          const nhapsachCollection: INhapsach[] = [{ id: 456 }];
          expectedResult = service.addNhapsachToCollectionIfMissing(nhapsachCollection, nhapsach);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nhapsach);
        });

        it('should add only unique Nhapsach to an array', () => {
          const nhapsachArray: INhapsach[] = [{ id: 123 }, { id: 456 }, { id: 68285 }];
          const nhapsachCollection: INhapsach[] = [{ id: 123 }];
          expectedResult = service.addNhapsachToCollectionIfMissing(nhapsachCollection, ...nhapsachArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const nhapsach: INhapsach = { id: 123 };
          const nhapsach2: INhapsach = { id: 456 };
          expectedResult = service.addNhapsachToCollectionIfMissing([], nhapsach, nhapsach2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nhapsach);
          expect(expectedResult).toContain(nhapsach2);
        });

        it('should accept null and undefined values', () => {
          const nhapsach: INhapsach = { id: 123 };
          expectedResult = service.addNhapsachToCollectionIfMissing([], null, nhapsach, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nhapsach);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
