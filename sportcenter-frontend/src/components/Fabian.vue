<template>
    <div class="hello">
        <div class = "section">
            <h2> Left TSection</h2>
            <div class="text-fields"> 
                <input type="text" placeholder="Field 1">
                <br>
                <input type="text" placeholder="Field 2">
                <br>
                <input type="text" placeholder="Field 3">
            </div>
            <button> Submit Left Section</button>
        </div>
        <div class = "section">
            <h2> Right Section</h2>
            <div class="text-fields">
                <input type="text" placeholder="Field 4">
                <br>
                <input type="text" placeholder="Field 5">
                <br>
                <input type="text" placeholder="Field 6">
                <br>
                <input type="text" placeholder="Field 7">
                <br>
                <input type="text" placeholder="Field 8">
            </div>
            <button> Submit Right Section</button>
        </div>
    </div>
</template>
  
<script>
export default {
    name: 'hello',
    data () {
        return {
            msg: 'Welcome to Your Vue.js App'
        }
    }
}
</script>
  
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .hello {
        display: flex;
}
  
.left,
.right {
    flex: 1;
}
  
h2 {
    font-weight: normal;
}

ul {
    list-style-type: none;
    padding: 0;
}

li {
    display: inline-block;
    margin: 0 10px;
}

a {
    color: #42b983;
}
</style>
  