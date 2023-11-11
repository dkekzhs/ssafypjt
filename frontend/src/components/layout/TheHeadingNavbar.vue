<script setup>

</script>

<template>
    <div>
        헤더 div
        <nav>
        nav 
        <router-link :to="{name : 'home'}">home</router-link>
        <router-link :to="{name : 'login'}">login</router-link>
        <router-link :to="{name : 'board'}">board</router-link>
    </nav>
    </div>

</template>

<style scoped>

</style>