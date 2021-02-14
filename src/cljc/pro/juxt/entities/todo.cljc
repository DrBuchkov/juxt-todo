(ns pro.juxt.entities.todo
  (:require [malli.core :as m]))

(def Todo
  [:map
   [:todo/title {:pro.juxt/form-opts {:label "What to do?"}} string?]
   [:todo/description {:optional           true
                       :pro.juxt/form-opts {:field :textarea}} string?]
   [:todo/status [:enum :todo :in-progress :done]]])

(def todo? (m/validator Todo))
