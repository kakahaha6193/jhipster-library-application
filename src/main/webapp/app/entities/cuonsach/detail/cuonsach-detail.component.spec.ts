import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CuonsachDetailComponent } from './cuonsach-detail.component';

describe('Component Tests', () => {
  describe('Cuonsach Management Detail Component', () => {
    let comp: CuonsachDetailComponent;
    let fixture: ComponentFixture<CuonsachDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CuonsachDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ cuonsach: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CuonsachDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CuonsachDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cuonsach on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cuonsach).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
