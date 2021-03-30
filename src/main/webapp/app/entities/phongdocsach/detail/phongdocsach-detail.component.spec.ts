import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PhongdocsachDetailComponent } from './phongdocsach-detail.component';

describe('Component Tests', () => {
  describe('Phongdocsach Management Detail Component', () => {
    let comp: PhongdocsachDetailComponent;
    let fixture: ComponentFixture<PhongdocsachDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PhongdocsachDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ phongdocsach: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PhongdocsachDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhongdocsachDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load phongdocsach on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.phongdocsach).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
