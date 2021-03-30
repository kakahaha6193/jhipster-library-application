import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ThuthuDetailComponent } from './thuthu-detail.component';

describe('Component Tests', () => {
  describe('Thuthu Management Detail Component', () => {
    let comp: ThuthuDetailComponent;
    let fixture: ComponentFixture<ThuthuDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ThuthuDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ thuthu: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ThuthuDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThuthuDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load thuthu on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.thuthu).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
