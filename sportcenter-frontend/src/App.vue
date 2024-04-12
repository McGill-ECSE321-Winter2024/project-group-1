<template>
  <div id="app">
    <HBox id="horizontalContainer">
      <VBox id="verticalContainer" style="margin-right: 25px;">
        <h1>FullForm</h1>
        <h2>Put yourself first<br>{{ $time }}</h2>
        <p>{{ getAccountType() }}</p>
      </VBox id="verticalContainer">
      <img src="./assets/gym-icon.jpg" alt="Logo" contain height="140px" width="140px" style="border-radius: 50%;" @click="goHome()"></img>
      <VBox v-if="$debugging_mode" style="margin-left: 15px;">
        <h3 style="font-style: italic;">Debugging</h3>
        <p>{{ $accountType }}</p>
        <p>{{ $username }}</p>
        <p v-if="$loggedIn">Logged in</p>
      </VBox>
    </HBox>
    <br>
    <HBox v-if="$loggedIn">
      <button id="menuButton" @click="goHome()">Home</button>
      <button id="menuButton" v-if="$accountType === 'Customer'" @click="goCustomerAccount()">Account</button>
      <button id="menuButton" v-if="$accountType === 'Instructor'" @click="goInstructorAccount()">Account</button>
      <button id="menuButton" v-if="$accountType === 'Owner'" @click="goOwnerAccount()">Account</button>
      <button id="menuButton" v-if="$accountType === 'Customer' || $accountType === 'Instructor'" @click="goMyActivities()">My activities</button>
      <button id="menuButton" @click="goActivity()">All activities</button>
      <button id="menuButton" v-if="$accountType != 'Owner'" @click="goInstructors()">Instructors</button>
      <button id="menuButton" v-if="$accountType=='Owner'" @click="goInstructorsForOwner()">Instructors</button>
      <button id="destroyButton" @click="goLogout()">Logout</button> <!--TO CHANGE-->
    </HBox>
    <HBox v-else>
      <button id="menuButton" @click="goLogin()">Login</button>
      <button id="menuButton" @click="goCreateAccount()">Create account</button>
    </HBox>
    <router-view style="margin-bottom: 80px;"></router-view>
    <p style="margin-bottom: 80px;">© 2024 FullForm (Group 1)</p>
  </div>
</template>

<script>
import Vue from 'vue';

export default {
    name: 'app',
    data() {
      return {
        accountType: localStorage.getItem('accountType') || 'Guest',
        username: localStorage.getItem('username') || 'JoeMama',
        loggedIn: localStorage.getItem('loggedIn') === 'true',
        time: localStorage.getItem('time') || (new Date().getDate() + "/" + (new Date().getMonth() + 1) + "/" + new Date().getFullYear()),
        debugging_mode: localStorage.getItem('debugging_mode') === 'true'
      };
    },
  methods: {
      getAccountType() {
        //localStorage.setItem('accountType', Math.random() < 0.5 ? "Customer" : "Instructor_1");
        // return localStorage.getItem('accountType')
      },
      setAccountType() {
        // this.accountType = Math.random() < 0.5 ? "Customer" : "Instructor_1";
        return "fml";
      },
      goHome() {
        this.$router.push('/');
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
        if (this.$accountType === 'Customer') {
          this.$router.push('/app/my-activities/customer');
        } else if (this.$accountType === 'Instructor') {
          this.$router.push('/app/my-activities/instructor');
        }
      },
      goActivity() {
        this.$router.push('/app/activity');
      },
      goLogin() {
        this.$router.push('/app/auth/login');
      },
      goLogout() {
        this.$router.push('/app/auth/login');
        this.$accountType = 'Guest';
        this.$username = 'JoeMama';
        this.$loggedIn = false;
      },
      goCreateAccount() {
        this.$router.push('/app/auth/createaccount');
      },
      goInstructors() {
        this.$router.push('/app/instructors/view-instructors');
      },
      goInstructorsForOwner() {
        this.$router.push('/app/instructors/manage-instructors');
      }
    }
  }
</script>

<style scoped src="./assets/main.css">
