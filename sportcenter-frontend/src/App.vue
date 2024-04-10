<template>
  <div id="app">
    <HBox id="horizontalContainer">
      <VBox id="verticalContainer" style="margin-right: 25px;">
        <h1>FullForm</h1>
        <h2>Put yourself first</h2>
      </VBox>
      <img src="./assets/gym-icon.jpg" alt="Logo" contain height="110px" width="110px" style="border-radius: 55px;" @click="goHome()"></img>
    </HBox>
    <br>
    <HBox v-if="$loggedIn">
      <button id="menuButton" v-if="$accountType=='Customer'" @click="goCustomerAccount()">Account</button>
      <button id="menuButton" v-if="$accountType=='Instructor'" @click="goInstructorAccount()">Account</button>
      <button id="menuButton" v-if="$accountType=='Owner'" @click="goOwnerAccount()">Account</button>
      <button id="menuButton" @click="goMyActivities()">My Activities</button>
      <button id="menuButton" @click="goActivity()">All activities</button>
      <button id="menuButton" @click="goInstructors()">Instructors</button>
      <button id="destroyButton" @click="goAuth()">Logout</button>
    </HBox>
    <HBox v-else>
      <button id="menuButton" @click="goAuth()">Login</button>
      <button id="menuButton" @click="goAuth()">Create account</button>
    </HBox>
    <router-view></router-view>
  </div>
</template>

<script>
import Vue from 'vue';

// 4 types: Guest, Customer, Instructor, Owner
Vue.prototype.$accountType = 'Customer';
Vue.prototype.$username = 'JoeMama'; // guest = JoeMama
Vue.prototype.$loggedIn = false;

export default {
    name: 'app',
    methods: {
      goHome() {
        this.$router.push('/app/home');
      },
      goCustomerAccount() {
        this.$router.push('/app/account/customer-account');
      },
      goInstructorAccount() {
        this.$router.push('/app/account/instructor-account');
      },
      goOwnerAccount() {
        this.$router.push('/app/account/owner-account');
      },
      goMyActivities() {
        if (true) {
          this.$router.push('/app/my-activities/customer');
        } else {
          this.$router.push('/app/my-activities/instructor');
        }
      },
      goActivity() {
        this.$router.push('/app/activity');
      },
      goAuth() {
        this.$router.push('/app/auth/login');
      },
      goInstructors() {
        this.$router.push('/app/instructors');
      }
    }
  }
</script>

<style scoped src="./assets/main.css">
