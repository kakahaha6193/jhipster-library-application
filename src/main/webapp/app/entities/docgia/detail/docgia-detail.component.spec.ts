import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DocgiaDetailComponent } from './docgia-detail.component';

describe('Component Tests', () => {
  describe('Docgia Management Detail Component', () => {
    let comp: DocgiaDetailComponent;
    let fixture: ComponentFixture<DocgiaDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DocgiaDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ docgia: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DocgiaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocgiaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load docgia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.docgia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
