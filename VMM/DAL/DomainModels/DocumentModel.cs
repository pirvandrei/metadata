﻿using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System.Collections.Generic;

namespace MongoDbAccessLayer.DomainModels
{
    public class DocumentModel
    {
        [BsonId]
        public string _id { get; set; }
        public string Name { get; set; }
        public string Source { get; set; }
        //public List<FeatureHub> Features { get; set; }
        public List<DescriptionHub> Description { get; set; }
    }
    //public class FeatureHub
    //{
    //    public string Date { get; set; }
    //    public List<FeatureSatellite> Satellite { get; set; }
    //}
    //public class FeatureSatellite
    //{
    //    public string Date { get; set; }
    //    public List<Satellite> Satellite { get; set; }
    //}

    //public class Satellite
    //{
    //    public List<Scene> scenes { get; set; }
    //    public List<Optical_Character_Recognition> optical_character_recognition { get; set; }
    //    public List<Speech_Recognition> speech_recognition { get; set; }
    //    public List<Sentiment_Analysis> sentiment_analysis { get; set; }
    //    [BsonElement("common_objects")]
    //    public List<CommonObjects> objects { get; set; }
    //}

    //public class Optical_Character_Recognition
    //{
    //    public int scene { get; set; }
    //    public string start { get; set; }
    //    public string end { get; set; }
    //    public int frameStart { get; set; }
    //    public int frameEnd { get; set; }
    //    public string value { get; set; }
    //}

    //public class Speech_Recognition
    //{
    //    public int scene { get; set; }
    //    public string start { get; set; }
    //    public string end { get; set; }
    //    public int frameStart { get; set; }
    //    public int frameEnd { get; set; }
    //    public string value { get; set; }
    //}

    //public class Sentiment_Analysis
    //{
    //    public int scene { get; set; }
    //    public string start { get; set; }
    //    public string end { get; set; }
    //    public int frameStart { get; set; }
    //    public int frameEnd { get; set; }
    //    public Sentiment_AnalysisValue value { get; set; }
    //}

    //public class Sentiment_AnalysisValue
    //{
    //    public double neg { get; set; }
    //    public double neu { get; set; }
    //    public double pos { get; set; }
    //    public double compound { get; set; }
    //}

    //public class CommonObjects
    //{
    //    public int scene { get; set; }
    //    public string start { get; set; }
    //    public string end { get; set; }
    //    public int frameStart { get; set; }
    //    public int frameEnd { get; set; }
    //    public List<ObjectValue> value { get; set; }
    //}

    //public class ObjectValue
    //{
    //    public string label { get; set; }
    //    public double confidence { get; set; }
    //}


    //public class Scene
    //{
    //    public int scene { get; set; }
    //    public string start { get; set; }
    //    public string end { get; set; }
    //    public int frameStart { get; set; }
    //    public int frameEnd { get; set; }
    //}


    public class DescriptionHub
    {
        public string Date { get; set; }
        public OMRSatellite Satellite { get; set; }
    }

    public class OMRSatellite
    {
        public string Name { get; set; }
        public List<OMRAttribute> Attributes { get; set; }
    }

    public class OMRAttribute
    {
        public string Name { get; set; }

        public string Value { get; set; }

        public string Standard { get; set; }
    }

    //public class OriginalAttributes
    //{
    //    public string Key { get; set; }

    //    public string Value { get; set; }
    //}





    public class DocModel
    {
        [BsonId]
        public ObjectId _id { get; set; }

        [BsonElement("WasDerivedFrom")]
        public string WasDerivedFromId { get; set; }
    }

    public class DocModelWithDescriptionModel
    {
        [BsonId]
        public ObjectId _id { get; set; }

        [BsonElement("WasDerivedFrom")]
        public string WasDerivedFrom { get; set; }

        public List<DescriptionModel> DescriptionModel { get; set; }
    }

    public class DocReadyForNode
    {
        [BsonId]
        public ObjectId DocumentId { get; set; }

        [BsonElement("WasDerivedFrom")]
        public string WasDerivedFromId { get; set; }

        public string Title { get; set; }
        public int Position { get; set; }
    }
}
