import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'sach',
        data: { pageTitle: 'libraryApplicationApp.sach.home.title' },
        loadChildren: () => import('./sach/sach.module').then(m => m.SachModule),
      },
      {
        path: 'cuonsach',
        data: { pageTitle: 'libraryApplicationApp.cuonsach.home.title' },
        loadChildren: () => import('./cuonsach/cuonsach.module').then(m => m.CuonsachModule),
      },
      {
        path: 'giasach',
        data: { pageTitle: 'libraryApplicationApp.giasach.home.title' },
        loadChildren: () => import('./giasach/giasach.module').then(m => m.GiasachModule),
      },
      {
        path: 'nhaxuatban',
        data: { pageTitle: 'libraryApplicationApp.nhaxuatban.home.title' },
        loadChildren: () => import('./nhaxuatban/nhaxuatban.module').then(m => m.NhaxuatbanModule),
      },
      {
        path: 'theloai',
        data: { pageTitle: 'libraryApplicationApp.theloai.home.title' },
        loadChildren: () => import('./theloai/theloai.module').then(m => m.TheloaiModule),
      },
      {
        path: 'phongdungsach',
        data: { pageTitle: 'libraryApplicationApp.phongdungsach.home.title' },
        loadChildren: () => import('./phongdungsach/phongdungsach.module').then(m => m.PhongdungsachModule),
      },
      {
        path: 'nhapsach',
        data: { pageTitle: 'libraryApplicationApp.nhapsach.home.title' },
        loadChildren: () => import('./nhapsach/nhapsach.module').then(m => m.NhapsachModule),
      },
      {
        path: 'phongdocsach',
        data: { pageTitle: 'libraryApplicationApp.phongdocsach.home.title' },
        loadChildren: () => import('./phongdocsach/phongdocsach.module').then(m => m.PhongdocsachModule),
      },
      {
        path: 'muonsach',
        data: { pageTitle: 'libraryApplicationApp.muonsach.home.title' },
        loadChildren: () => import('./muonsach/muonsach.module').then(m => m.MuonsachModule),
      },
      {
        path: 'thuephong',
        data: { pageTitle: 'libraryApplicationApp.thuephong.home.title' },
        loadChildren: () => import('./thuephong/thuephong.module').then(m => m.ThuephongModule),
      },
      {
        path: 'thuthu',
        data: { pageTitle: 'libraryApplicationApp.thuthu.home.title' },
        loadChildren: () => import('./thuthu/thuthu.module').then(m => m.ThuthuModule),
      },
      {
        path: 'docgia',
        data: { pageTitle: 'libraryApplicationApp.docgia.home.title' },
        loadChildren: () => import('./docgia/docgia.module').then(m => m.DocgiaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
