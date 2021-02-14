(ns pro.juxt.core
  (:require [pro.juxt.components.sidenav :refer [sidenav]]
            [pro.juxt.todo.routes :as todo]
            [pro.juxt.utils :as utils]
            [pro.juxt.events]
            [mount.core :as mount]
            [reagent.dom :as rd]
            [re-frame.core :as rf]
            [reitit.core :as reitit]
            [reitit.frontend.easy :as rfe]))

(defn app []
  (if-let [page @(rf/subscribe [:common/page])]
    [:div.min-h-screen.flex.flex-auto.flex-shrink-0.antialiased.bg-gray-50.text-gray-800
     [sidenav "TODOer" [{:title    "TODOs"
                         :children [{:title "Create TODO"
                                     :icon  "fa-pencil"
                                     :uri   "#/todo/create"
                                     :page  :todo/create}
                                    {:title "List TODOs"
                                     :icon  "fa-list"
                                     :uri   "#/todo/list"
                                     :page  :todo/list}]}]]
     [:div
      {:class "flex flex-col w-10/12 bg-white h-100vh border-r p-6"}
      [page]]]))

(def router
  (reitit/router
    [["/" {:controllers [{:start (fn [_] (utils/redirect-page! "#/todo/create"))}]}]
     (todo/routes)]))

(defn navigate! [match _]
  (rf/dispatch [:common/navigate match]))

(defn start-router! []
  (rfe/start!
    router
    navigate!
    {}))

(defn mount-components! []
  (mount/start))

(defn ^:dev/after-load mount! []
  (rf/clear-subscription-cache!)
  (rd/render [app] (.getElementById js/document "app")))

(defn init! []
  (mount-components!)
  (start-router!)
  (mount!))

