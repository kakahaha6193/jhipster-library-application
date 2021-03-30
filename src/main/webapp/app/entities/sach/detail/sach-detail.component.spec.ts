import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SachDetailComponent } from './sach-detail.component';

describe('Component Tests', () => {
  describe('Sach Management Detail Component', () => {
    let comp: SachDetailComponent;
    let fixture: ComponentFixture<SachDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [SachDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ sach: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(SachDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SachDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sach on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sach).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
