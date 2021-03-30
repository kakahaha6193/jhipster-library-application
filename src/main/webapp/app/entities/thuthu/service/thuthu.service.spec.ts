import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IThuthu, Thuthu } from '../thuthu.model';

import { ThuthuService } from './thuthu.service';

describe('Service Tests', () => {
  describe('Thuthu Service', () => {
    let service: ThuthuService;
    let httpMock: HttpTestingController;
    let elemDefault: IThuthu;
    let expectedResult: IThuthu | IThuthu[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ThuthuService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        hoTen: 'AAAAAAA',
        username: 'AAAAAAA',
        password: 'AAAAAAA',
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

      it('should create a Thuthu', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Thuthu()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Thuthu', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            hoTen: 'BBBBBB',
            username: 'BBBBBB',
            password: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Thuthu', () => {
        const patchObject = Object.assign(
          {
            hoTen: 'BBBBBB',
          },
          new Thuthu()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Thuthu', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            hoTen: 'BBBBBB',
            username: 'BBBBBB',
            password: 'BBBBBB',
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

      it('should delete a Thuthu', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addThuthuToCollectionIfMissing', () => {
        it('should add a Thuthu to an empty array', () => {
          const thuthu: IThuthu = { id: 123 };
          expectedResult = service.addThuthuToCollectionIfMissing([], thuthu);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(thuthu);
        });

        it('should not add a Thuthu to an array that contains it', () => {
          const thuthu: IThuthu = { id: 123 };
          const thuthuCollection: IThuthu[] = [
            {
              ...thuthu,
            },
            { id: 456 },
          ];
          expectedResult = service.addThuthuToCollectionIfMissing(thuthuCollection, thuthu);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Thuthu to an array that doesn't contain it", () => {
          const thuthu: IThuthu = { id: 123 };
          const thuthuCollection: IThuthu[] = [{ id: 456 }];
          expectedResult = service.addThuthuToCollectionIfMissing(thuthuCollection, thuthu);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(thuthu);
        });

        it('should add only unique Thuthu to an array', () => {
          const thuthuArray: IThuthu[] = [{ id: 123 }, { id: 456 }, { id: 23996 }];
          const thuthuCollection: IThuthu[] = [{ id: 123 }];
          expectedResult = service.addThuthuToCollectionIfMissing(thuthuCollection, ...thuthuArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const thuthu: IThuthu = { id: 123 };
          const thuthu2: IThuthu = { id: 456 };
          expectedResult = service.addThuthuToCollectionIfMissing([], thuthu, thuthu2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(thuthu);
          expect(expectedResult).toContain(thuthu2);
        });

        it('should accept null and undefined values', () => {
          const thuthu: IThuthu = { id: 123 };
          expectedResult = service.addThuthuToCollectionIfMissing([], null, thuthu, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(thuthu);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
