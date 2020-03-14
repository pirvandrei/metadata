﻿using MongoDbAccessLayer.DataService.Dtos;
using System.Collections.Generic;

namespace Interface.Models
{
    public class SearchVideoScenesModel 
    {
        public List<Scene> Scenes { get; set; }
        public string SearchQuery { get; set; }
    }
}