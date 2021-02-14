(ns pro.juxt.components.sidenav
  (:require [re-frame.core :as rf]))

(defn nav-link [uri title page]
  [:a {:href  uri
       :class (when (= page @(rf/subscribe [:common/page])) :is-active)}
   title])

(defn submenu-child [{:keys [title icon uri page]}]
  [:li
   [:a.relative.flex.flex-row.items-center.h-11.focus:outline-none.hover:bg-gray-50.text-gray-600.hover:text-gray-800.border-l-4.border-transparent.hover:border-indigo-500.pr-6
    {:href  uri
     :class (when (= page @(rf/subscribe [:common/page])) :is-active)}
    [:span.inline-flex.justify-center.items-center.ml-4
     [:i {:class (str "fa " icon)}]]
    [:span.ml-2.text-sm.tracking-wide.truncate
     title]]])

(defn submenu [opts]
  (let [{:keys [title children]} opts]
    [:<>
     [:li.px-5
      [:div.flex.flex-row.items-center.h-8
       [:div.text-sm.font-light.tracking-wide.text-gray-500 title]]]
     (for [child children]
       ^{:key (:title child)} [submenu-child child])]))

(defn sidenav [title submenus]
  [:div
   {:class "flex flex-col w-2/12 bg-white h-100vh border-r"}
   [:div.flex.items-center.justify-center.h-14.border-b
    [:div title]]
   [:div.overflow-y-auto.overflow-x-hidden.flex-grow
    [:ul.flex.flex-col.py-4.space-y-1
     (for [menu submenus]
       ^{:key (:title menu)} [submenu menu])]]])
