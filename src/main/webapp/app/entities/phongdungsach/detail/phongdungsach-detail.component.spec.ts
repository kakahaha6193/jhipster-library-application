import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PhongdungsachDetailComponent } from './phongdungsach-detail.component';

describe('Component Tests', () => {
  describe('Phongdungsach Management Detail Component', () => {
    let comp: PhongdungsachDetailComponent;
    let fixture: ComponentFixture<PhongdungsachDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PhongdungsachDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ phongdungsach: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PhongdungsachDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhongdungsachDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load phongdungsach on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.phongdungsach).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
