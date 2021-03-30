import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITheloai, Theloai } from '../theloai.model';

import { TheloaiService } from './theloai.service';

describe('Service Tests', () => {
  describe('Theloai Service', () => {
    let service: TheloaiService;
    let httpMock: HttpTestingController;
    let elemDefault: ITheloai;
    let expectedResult: ITheloai | ITheloai[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TheloaiService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tenTheLoai: 'AAAAAAA',
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

      it('should create a Theloai', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Theloai()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Theloai', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenTheLoai: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Theloai', () => {
        const patchObject = Object.assign({}, new Theloai());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Theloai', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenTheLoai: 'BBBBBB',
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

      it('should delete a Theloai', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTheloaiToCollectionIfMissing', () => {
        it('should add a Theloai to an empty array', () => {
          const theloai: ITheloai = { id: 123 };
          expectedResult = service.addTheloaiToCollectionIfMissing([], theloai);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(theloai);
        });

        it('should not add a Theloai to an array that contains it', () => {
          const theloai: ITheloai = { id: 123 };
          const theloaiCollection: ITheloai[] = [
            {
              ...theloai,
            },
            { id: 456 },
          ];
          expectedResult = service.addTheloaiToCollectionIfMissing(theloaiCollection, theloai);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Theloai to an array that doesn't contain it", () => {
          const theloai: ITheloai = { id: 123 };
          const theloaiCollection: ITheloai[] = [{ id: 456 }];
          expectedResult = service.addTheloaiToCollectionIfMissing(theloaiCollection, theloai);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(theloai);
        });

        it('should add only unique Theloai to an array', () => {
          const theloaiArray: ITheloai[] = [{ id: 123 }, { id: 456 }, { id: 64049 }];
          const theloaiCollection: ITheloai[] = [{ id: 123 }];
          expectedResult = service.addTheloaiToCollectionIfMissing(theloaiCollection, ...theloaiArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const theloai: ITheloai = { id: 123 };
          const theloai2: ITheloai = { id: 456 };
          expectedResult = service.addTheloaiToCollectionIfMissing([], theloai, theloai2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(theloai);
          expect(expectedResult).toContain(theloai2);
        });

        it('should accept null and undefined values', () => {
          const theloai: ITheloai = { id: 123 };
          expectedResult = service.addTheloaiToCollectionIfMissing([], null, theloai, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(theloai);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
