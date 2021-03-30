import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TheloaiDetailComponent } from './theloai-detail.component';

describe('Component Tests', () => {
  describe('Theloai Management Detail Component', () => {
    let comp: TheloaiDetailComponent;
    let fixture: ComponentFixture<TheloaiDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TheloaiDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ theloai: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TheloaiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TheloaiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load theloai on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.theloai).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
