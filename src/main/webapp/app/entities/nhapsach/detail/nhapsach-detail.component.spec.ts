import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NhapsachDetailComponent } from './nhapsach-detail.component';

describe('Component Tests', () => {
  describe('Nhapsach Management Detail Component', () => {
    let comp: NhapsachDetailComponent;
    let fixture: ComponentFixture<NhapsachDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [NhapsachDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ nhapsach: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(NhapsachDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NhapsachDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nhapsach on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nhapsach).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
