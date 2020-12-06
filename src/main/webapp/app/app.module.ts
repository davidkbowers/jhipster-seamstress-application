import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { JhipsterSeamstressApplicationSharedModule } from 'app/shared/shared.module';
import { JhipsterSeamstressApplicationCoreModule } from 'app/core/core.module';
import { JhipsterSeamstressApplicationAppRoutingModule } from './app-routing.module';
import { JhipsterSeamstressApplicationHomeModule } from './home/home.module';
import { JhipsterSeamstressApplicationEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    JhipsterSeamstressApplicationSharedModule,
    JhipsterSeamstressApplicationCoreModule,
    JhipsterSeamstressApplicationHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JhipsterSeamstressApplicationEntityModule,
    JhipsterSeamstressApplicationAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class JhipsterSeamstressApplicationAppModule {}
