import { Component, OnInit } from '@angular/core';
import { TagsService } from './tags.service';
import {Tag} from './Tag'

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.css']
})
export class TagsComponent implements OnInit {

  constructor(private tagsService : TagsService) { }

  ngOnInit(){}

}
