import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GiasachDetailComponent } from './giasach-detail.component';

describe('Component Tests', () => {
  describe('Giasach Management Detail Component', () => {
    let comp: GiasachDetailComponent;
    let fixture: ComponentFixture<GiasachDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [GiasachDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ giasach: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(GiasachDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GiasachDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load giasach on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.giasach).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
