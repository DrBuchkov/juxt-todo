(ns pro.juxt.db.todo
  (:require [pro.juxt.db.operations :as op]
            [pro.juxt.entities.todo :as todo]))

(def create! (op/create!-fn todo/Todo))

(def browse (op/browse-fn todo/Todo))

(def fetch (op/fetch-fn))

(def edit! (op/edit!-fn todo/Todo fetch))

(def delete! (op/delete!-fn fetch))