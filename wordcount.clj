(ns wordcount
  (:use     [streamparse.specs])
  (:gen-class))

(defn wordcount [options]
  [
    ;; spout configuration
    {"sent-spout" (python-spout-spec
          options
          "spouts.sentences.Sentences"
          ["sentence"]
          )
    }

    ;; bolt configuration
    {"parse-bolt1" (python-bolt-spec
          options
          {"sent-spout" :shuffle}
          "bolts.parse.ParseTweet"
          ["valid_words"]
          :p 2)
     "parse-bolt2" (python-bolt-spec
          options
          {"sent-spout" :shuffle}
          "bolts.parse.ParseTweet"
          ["valid_words"]
          :p 2)
     "count-bolt" (python-bolt-spec
          options
          {"parse-bolt1" :shuffle
           "parse-bolt2" :shuffle}
          "bolts.tweetcounter.TweetCounter"
          ["word" "counts"]
          :p 2)
    }
  ]
)