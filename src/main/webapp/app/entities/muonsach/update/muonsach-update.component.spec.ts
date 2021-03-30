jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MuonsachService } from '../service/muonsach.service';
import { IMuonsach, Muonsach } from '../muonsach.model';
import { IDocgia } from 'app/entities/docgia/docgia.model';
import { DocgiaService } from 'app/entities/docgia/service/docgia.service';
import { IThuthu } from 'app/entities/thuthu/thuthu.model';
import { ThuthuService } from 'app/entities/thuthu/service/thuthu.service';

import { MuonsachUpdateComponent } from './muonsach-update.component';

describe('Component Tests', () => {
  describe('Muonsach Management Update Component', () => {
    let comp: MuonsachUpdateComponent;
    let fixture: ComponentFixture<MuonsachUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let muonsachService: MuonsachService;
    let docgiaService: DocgiaService;
    let thuthuService: ThuthuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MuonsachUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(MuonsachUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MuonsachUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      muonsachService = TestBed.inject(MuonsachService);
      docgiaService = TestBed.inject(DocgiaService);
      thuthuService = TestBed.inject(ThuthuService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Docgia query and add missing value', () => {
        const muonsach: IMuonsach = { id: 456 };
        const docgia: IDocgia = { id: 10686 };
        muonsach.docgia = docgia;
        const docgia: IDocgia = { id: 5640 };
        muonsach.docgia = docgia;

        const docgiaCollection: IDocgia[] = [{ id: 9754 }];
        spyOn(docgiaService, 'query').and.returnValue(of(new HttpResponse({ body: docgiaCollection })));
        const additionalDocgias = [docgia, docgia];
        const expectedCollection: IDocgia[] = [...additionalDocgias, ...docgiaCollection];
        spyOn(docgiaService, 'addDocgiaToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ muonsach });
        comp.ngOnInit();

        expect(docgiaService.query).toHaveBeenCalled();
        expect(docgiaService.addDocgiaToCollectionIfMissing).toHaveBeenCalledWith(docgiaCollection, ...additionalDocgias);
        expect(comp.docgiasSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Thuthu query and add missing value', () => {
        const muonsach: IMuonsach = { id: 456 };
        const thuthu: IThuthu = { id: 47543 };
        muonsach.thuthu = thuthu;

        const thuthuCollection: IThuthu[] = [{ id: 50414 }];
        spyOn(thuthuService, 'query').and.returnValue(of(new HttpResponse({ body: thuthuCollection })));
        const additionalThuthus = [thuthu];
        const expectedCollection: IThuthu[] = [...additionalThuthus, ...thuthuCollection];
        spyOn(thuthuService, 'addThuthuToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ muonsach });
        comp.ngOnInit();

        expect(thuthuService.query).toHaveBeenCalled();
        expect(thuthuService.addThuthuToCollectionIfMissing).toHaveBeenCalledWith(thuthuCollection, ...additionalThuthus);
        expect(comp.thuthusSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const muonsach: IMuonsach = { id: 456 };
        const docgia: IDocgia = { id: 24759 };
        muonsach.docgia = docgia;
        const docgia: IDocgia = { id: 82313 };
        muonsach.docgia = docgia;
        const thuthu: IThuthu = { id: 86211 };
        muonsach.thuthu = thuthu;

        activatedRoute.data = of({ muonsach });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(muonsach));
        expect(comp.docgiasSharedCollection).toContain(docgia);
        expect(comp.docgiasSharedCollection).toContain(docgia);
        expect(comp.thuthusSharedCollection).toContain(thuthu);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const muonsach = { id: 123 };
        spyOn(muonsachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ muonsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: muonsach }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(muonsachService.update).toHaveBeenCalledWith(muonsach);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const muonsach = new Muonsach();
        spyOn(muonsachService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ muonsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: muonsach }));
        saveSubject.complete();

        // THEN
        expect(muonsachService.create).toHaveBeenCalledWith(muonsach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const muonsach = { id: 123 };
        spyOn(muonsachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ muonsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(muonsachService.update).toHaveBeenCalledWith(muonsach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackDocgiaById', () => {
        it('Should return tracked Docgia primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDocgiaById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackThuthuById', () => {
        it('Should return tracked Thuthu primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackThuthuById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
