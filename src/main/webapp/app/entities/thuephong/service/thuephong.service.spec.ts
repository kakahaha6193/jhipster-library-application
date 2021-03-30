import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IThuephong, Thuephong } from '../thuephong.model';

import { ThuephongService } from './thuephong.service';

describe('Service Tests', () => {
  describe('Thuephong Service', () => {
    let service: ThuephongService;
    let httpMock: HttpTestingController;
    let elemDefault: IThuephong;
    let expectedResult: IThuephong | IThuephong[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ThuephongService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        ngayThue: currentDate,
        ca: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            ngayThue: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Thuephong', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            ngayThue: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayThue: currentDate,
          },
          returnedFromService
        );

        service.create(new Thuephong()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Thuephong', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            ngayThue: currentDate.format(DATE_TIME_FORMAT),
            ca: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayThue: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Thuephong', () => {
        const patchObject = Object.assign(
          {
            ngayThue: currentDate.format(DATE_TIME_FORMAT),
            ca: 1,
          },
          new Thuephong()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            ngayThue: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Thuephong', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            ngayThue: currentDate.format(DATE_TIME_FORMAT),
            ca: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayThue: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Thuephong', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addThuephongToCollectionIfMissing', () => {
        it('should add a Thuephong to an empty array', () => {
          const thuephong: IThuephong = { id: 123 };
          expectedResult = service.addThuephongToCollectionIfMissing([], thuephong);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(thuephong);
        });

        it('should not add a Thuephong to an array that contains it', () => {
          const thuephong: IThuephong = { id: 123 };
          const thuephongCollection: IThuephong[] = [
            {
              ...thuephong,
            },
            { id: 456 },
          ];
          expectedResult = service.addThuephongToCollectionIfMissing(thuephongCollection, thuephong);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Thuephong to an array that doesn't contain it", () => {
          const thuephong: IThuephong = { id: 123 };
          const thuephongCollection: IThuephong[] = [{ id: 456 }];
          expectedResult = service.addThuephongToCollectionIfMissing(thuephongCollection, thuephong);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(thuephong);
        });

        it('should add only unique Thuephong to an array', () => {
          const thuephongArray: IThuephong[] = [{ id: 123 }, { id: 456 }, { id: 68259 }];
          const thuephongCollection: IThuephong[] = [{ id: 123 }];
          expectedResult = service.addThuephongToCollectionIfMissing(thuephongCollection, ...thuephongArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const thuephong: IThuephong = { id: 123 };
          const thuephong2: IThuephong = { id: 456 };
          expectedResult = service.addThuephongToCollectionIfMissing([], thuephong, thuephong2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(thuephong);
          expect(expectedResult).toContain(thuephong2);
        });

        it('should accept null and undefined values', () => {
          const thuephong: IThuephong = { id: 123 };
          expectedResult = service.addThuephongToCollectionIfMissing([], null, thuephong, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(thuephong);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
