<template>
  <div id="app">
    <img src="./assets/logo_internet.png" width=25%>
    <br>
    <h1><b>SportCenter</b></h1>
    <h2><i>Get fit, not fat!</i></h2>
    <button id="homeButton" @click="goHome()">Home</button>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app',
    methods: {
      goHome() {
        this.$router.push('/app/home');
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
    margin-top: 15%;
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
</style>
