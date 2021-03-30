import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISach, Sach } from '../sach.model';

import { SachService } from './sach.service';

describe('Service Tests', () => {
  describe('Sach Service', () => {
    let service: SachService;
    let httpMock: HttpTestingController;
    let elemDefault: ISach;
    let expectedResult: ISach | ISach[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(SachService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tenSach: 'AAAAAAA',
        giaNiemYet: 0,
        tacgia: 'AAAAAAA',
        giaThue: 0,
        nganXep: 'AAAAAAA',
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

      it('should create a Sach', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Sach()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Sach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenSach: 'BBBBBB',
            giaNiemYet: 1,
            tacgia: 'BBBBBB',
            giaThue: 1,
            nganXep: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Sach', () => {
        const patchObject = Object.assign(
          {
            tacgia: 'BBBBBB',
          },
          new Sach()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Sach', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenSach: 'BBBBBB',
            giaNiemYet: 1,
            tacgia: 'BBBBBB',
            giaThue: 1,
            nganXep: 'BBBBBB',
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

      it('should delete a Sach', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addSachToCollectionIfMissing', () => {
        it('should add a Sach to an empty array', () => {
          const sach: ISach = { id: 123 };
          expectedResult = service.addSachToCollectionIfMissing([], sach);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(sach);
        });

        it('should not add a Sach to an array that contains it', () => {
          const sach: ISach = { id: 123 };
          const sachCollection: ISach[] = [
            {
              ...sach,
            },
            { id: 456 },
          ];
          expectedResult = service.addSachToCollectionIfMissing(sachCollection, sach);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Sach to an array that doesn't contain it", () => {
          const sach: ISach = { id: 123 };
          const sachCollection: ISach[] = [{ id: 456 }];
          expectedResult = service.addSachToCollectionIfMissing(sachCollection, sach);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(sach);
        });

        it('should add only unique Sach to an array', () => {
          const sachArray: ISach[] = [{ id: 123 }, { id: 456 }, { id: 32850 }];
          const sachCollection: ISach[] = [{ id: 123 }];
          expectedResult = service.addSachToCollectionIfMissing(sachCollection, ...sachArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const sach: ISach = { id: 123 };
          const sach2: ISach = { id: 456 };
          expectedResult = service.addSachToCollectionIfMissing([], sach, sach2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(sach);
          expect(expectedResult).toContain(sach2);
        });

        it('should accept null and undefined values', () => {
          const sach: ISach = { id: 123 };
          expectedResult = service.addSachToCollectionIfMissing([], null, sach, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(sach);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
