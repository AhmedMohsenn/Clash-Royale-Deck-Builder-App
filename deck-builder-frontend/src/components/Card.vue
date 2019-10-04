<template>
  <div class="card">
    <img :src="getCardImage(card)"
         alt="card image">

    <div class="card__elixir">
      {{ card.elixirCost }}
    </div>
  </div>
</template>

<script>
export default {
  props: {
    card: {
      type: Object
    }
  },

  data() {
    return {
      cardImage: null
    };
  },

  methods: {
    getCardImage: function(card) {
      return `http://www.clashapi.xyz/images/cards/${card.idName}.png`;
    }
  }
};
</script>

<style scoped>
.card {
  border: none;
}

.card > img {
  width: 10rem;
}

.card__elixir {
  position: absolute;
  top: 0;
  left: 0;
  color: white;
  background-color: #e960ec;
  border-radius: 50%;
  padding: 1rem;
  text-align: center;
  width: 30px;
  height: 30px;
}

/*
.numberCircle {
    border-radius: 50%;
    width: 36px;
    height: 36px;
    padding: 8px;

    background: #fff;
    border: 2px solid #666;
    color: #666;
    text-align: center;

    font: 32px Arial, sans-serif;
}
*/
</style>
