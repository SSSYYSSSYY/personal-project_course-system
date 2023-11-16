import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/admin-login',
      name: 'AdminLogin',
      component: () => import("../views/AdminLogin.vue")
    },
    {
      path: '/forgot-pwd/:identity',
      name: "ForgotPwd",
      component: () => import("../views/ForgotPwd.vue"),
      props: true,
    },
    {
      path: '/student-login',
      name: 'StudentLogin',
      component: () => import("../views/StudentLogin.vue")
    },
    {
      path: '/account-normal-admin',
      name: 'AccountNormalAdmin',
      component: () => import("../views/AccountNormalAdmin.vue")
    },
    {
      path: '/account-super-admin',
      name: 'AccountSuperAdmin',
      component: () => import("../views/AccountSuperAdmin.vue")
    },
    {
      path: '/account-student',
      name: 'AccountStudent',
      component: () => import("../views/AccountStudent.vue")
    },
    {
      path: '/courses',
      name: 'Courses',
      component: () => import("../views/Courses.vue")
    },
    {
      path: '/courses/:courseCode?=:identity',
      name: 'Course',
      component: () => import("../views/Course.vue"),
      props: true,
    },
    {
      path: '/new-course?=:identity',
      name: 'NewCourse',
      component: () => import("../views/NewCourse.vue"),
      props: true,
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'ErrorPage',
      component: () => import("../views/ErrorPage.vue"),
    }

  ]
})

export default router
