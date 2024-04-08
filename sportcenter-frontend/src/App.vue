<template>
  <div id="app">
    <!-- <img src="./assets/logo_internet.png" width=200px> -->
    <h1><b>SportCenter</b></h1>
    <h2><i>Get fit, not fat!</i></h2>

    <HBox>
      <button id="homeButton" @click="goHome()">Home</button>
      <button id="homeButton" @click="goAccount()">Account</button>
      <button id="homeButton" @click="goActivity()">Activity</button>
      <button id="logoutButton" @click="goAuth()">Logout</button>
    </HBox>
    
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app',
  methods: {
    goHome() {
      this.$router.push('/app/home');
    },
    goAccount() {
      this.$router.push('/app/account');
    },
    goActivity() {
      this.$router.push('/app/activity');
    },
    goAuth() {
      this.$router.push('/app/auth');
    }
  }
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 30px;
    align-content: center;
    display: flex;
    flex-direction: column;
    justify-items: center;
    align-items: center;
  }

  #homeButton {
    background-color: #4CAF50;
    border: none;
    color: white;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    border-radius: 12px;
    padding: 10px 24px;
  }

  #logoutButton {
    background-color: #ff0000;
    border: none;
    color: white;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    border-radius: 12px;
    padding: 10px 24px;
  }
</style>
