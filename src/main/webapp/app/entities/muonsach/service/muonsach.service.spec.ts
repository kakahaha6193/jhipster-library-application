import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMuonsach, Muonsach } from '../muonsach.model';

import { MuonsachService } from './muonsach.service';

describe('Service Tests', () => {
  describe('Muonsach Service', () => {
    let service: MuonsachService;
    let httpMock: HttpTestingController;
    let elemDefault: IMuonsach;
    let expectedResult: IMuonsach | IMuonsach[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(MuonsachService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        ngayMuon: currentDate,
        hanTra: currentDate,
        ngayTra: currentDate,
        trangThai: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            ngayMuon: currentDate.format(DATE_TIME_FORMAT),
            hanTra: currentDate.format(DATE_TIME_FORMAT),
            ngayTra: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Muonsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            ngayMuon: currentDate.format(DATE_TIME_FORMAT),
            hanTra: currentDate.format(DATE_TIME_FORMAT),
            ngayTra: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayMuon: currentDate,
            hanTra: currentDate,
            ngayTra: currentDate,
          },
          returnedFromService
        );

        service.create(new Muonsach()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Muonsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            ngayMuon: currentDate.format(DATE_TIME_FORMAT),
            hanTra: currentDate.format(DATE_TIME_FORMAT),
            ngayTra: currentDate.format(DATE_TIME_FORMAT),
            trangThai: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayMuon: currentDate,
            hanTra: currentDate,
            ngayTra: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Muonsach', () => {
        const patchObject = Object.assign({}, new Muonsach());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            ngayMuon: currentDate,
            hanTra: currentDate,
            ngayTra: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Muonsach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            ngayMuon: currentDate.format(DATE_TIME_FORMAT),
            hanTra: currentDate.format(DATE_TIME_FORMAT),
            ngayTra: currentDate.format(DATE_TIME_FORMAT),
            trangThai: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayMuon: currentDate,
            hanTra: currentDate,
            ngayTra: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Muonsach', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMuonsachToCollectionIfMissing', () => {
        it('should add a Muonsach to an empty array', () => {
          const muonsach: IMuonsach = { id: 123 };
          expectedResult = service.addMuonsachToCollectionIfMissing([], muonsach);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(muonsach);
        });

        it('should not add a Muonsach to an array that contains it', () => {
          const muonsach: IMuonsach = { id: 123 };
          const muonsachCollection: IMuonsach[] = [
            {
              ...muonsach,
            },
            { id: 456 },
          ];
          expectedResult = service.addMuonsachToCollectionIfMissing(muonsachCollection, muonsach);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Muonsach to an array that doesn't contain it", () => {
          const muonsach: IMuonsach = { id: 123 };
          const muonsachCollection: IMuonsach[] = [{ id: 456 }];
          expectedResult = service.addMuonsachToCollectionIfMissing(muonsachCollection, muonsach);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(muonsach);
        });

        it('should add only unique Muonsach to an array', () => {
          const muonsachArray: IMuonsach[] = [{ id: 123 }, { id: 456 }, { id: 56554 }];
          const muonsachCollection: IMuonsach[] = [{ id: 123 }];
          expectedResult = service.addMuonsachToCollectionIfMissing(muonsachCollection, ...muonsachArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const muonsach: IMuonsach = { id: 123 };
          const muonsach2: IMuonsach = { id: 456 };
          expectedResult = service.addMuonsachToCollectionIfMissing([], muonsach, muonsach2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(muonsach);
          expect(expectedResult).toContain(muonsach2);
        });

        it('should accept null and undefined values', () => {
          const muonsach: IMuonsach = { id: 123 };
          expectedResult = service.addMuonsachToCollectionIfMissing([], null, muonsach, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(muonsach);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
