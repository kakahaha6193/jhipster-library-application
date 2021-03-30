import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDocgia, Docgia } from '../docgia.model';

import { DocgiaService } from './docgia.service';

describe('Service Tests', () => {
  describe('Docgia Service', () => {
    let service: DocgiaService;
    let httpMock: HttpTestingController;
    let elemDefault: IDocgia;
    let expectedResult: IDocgia | IDocgia[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DocgiaService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        hoTen: 'AAAAAAA',
        ngaySinh: currentDate,
        diaChi: 'AAAAAAA',
        cmt: 'AAAAAAA',
        trangThai: 0,
        tienCoc: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            ngaySinh: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Docgia', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            ngaySinh: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngaySinh: currentDate,
          },
          returnedFromService
        );

        service.create(new Docgia()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Docgia', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            hoTen: 'BBBBBB',
            ngaySinh: currentDate.format(DATE_TIME_FORMAT),
            diaChi: 'BBBBBB',
            cmt: 'BBBBBB',
            trangThai: 1,
            tienCoc: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngaySinh: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Docgia', () => {
        const patchObject = Object.assign(
          {
            hoTen: 'BBBBBB',
            ngaySinh: currentDate.format(DATE_TIME_FORMAT),
            diaChi: 'BBBBBB',
            tienCoc: 1,
          },
          new Docgia()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            ngaySinh: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Docgia', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            hoTen: 'BBBBBB',
            ngaySinh: currentDate.format(DATE_TIME_FORMAT),
            diaChi: 'BBBBBB',
            cmt: 'BBBBBB',
            trangThai: 1,
            tienCoc: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngaySinh: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Docgia', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDocgiaToCollectionIfMissing', () => {
        it('should add a Docgia to an empty array', () => {
          const docgia: IDocgia = { id: 123 };
          expectedResult = service.addDocgiaToCollectionIfMissing([], docgia);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(docgia);
        });

        it('should not add a Docgia to an array that contains it', () => {
          const docgia: IDocgia = { id: 123 };
          const docgiaCollection: IDocgia[] = [
            {
              ...docgia,
            },
            { id: 456 },
          ];
          expectedResult = service.addDocgiaToCollectionIfMissing(docgiaCollection, docgia);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Docgia to an array that doesn't contain it", () => {
          const docgia: IDocgia = { id: 123 };
          const docgiaCollection: IDocgia[] = [{ id: 456 }];
          expectedResult = service.addDocgiaToCollectionIfMissing(docgiaCollection, docgia);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(docgia);
        });

        it('should add only unique Docgia to an array', () => {
          const docgiaArray: IDocgia[] = [{ id: 123 }, { id: 456 }, { id: 46374 }];
          const docgiaCollection: IDocgia[] = [{ id: 123 }];
          expectedResult = service.addDocgiaToCollectionIfMissing(docgiaCollection, ...docgiaArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const docgia: IDocgia = { id: 123 };
          const docgia2: IDocgia = { id: 456 };
          expectedResult = service.addDocgiaToCollectionIfMissing([], docgia, docgia2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(docgia);
          expect(expectedResult).toContain(docgia2);
        });

        it('should accept null and undefined values', () => {
          const docgia: IDocgia = { id: 123 };
          expectedResult = service.addDocgiaToCollectionIfMissing([], null, docgia, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(docgia);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
