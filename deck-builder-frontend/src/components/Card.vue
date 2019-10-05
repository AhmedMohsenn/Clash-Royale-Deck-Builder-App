<template>
  <div class="card"
       v-click-outside="clearEvents"
       @click="getPossibleEvents()"
       :class="{'empty-card' : isEmpty}">

    <font-awesome-icon icon="plus"
                       v-if="isEmpty"
                       class="add-card-icon"
                       size="sm" />

    <img v-if="!isEmpty"
         :src="getCardImage(card)"
         alt="card image" />

    <div v-if="!isEmpty"
         class="card__elixir">
      <p>{{ card.elixirCost }}</p>
    </div>

    <div class="action-btns">
      <b-button block
                @click="$emit('select-card')"
                v-if="events.includes('select')">Select</b-button>
      <b-button block
                v-if="events.includes('moreInfo')">Info</b-button>
      <b-button block
                v-if="events.includes('remove')">Remove</b-button>
      <b-button block
                v-if="events.includes('upgrade')">Upgrade</b-button>
    </div>
  </div>
</template>

<script>
import ClickOutside from "vue-click-outside";
export default {
  props: {
    card: {
      type: Object
    },
    handleClickAction: {
      type: Function
    }
  },

  data() {
    return {
      cardImage: null,
      events: []
    };
  },

  computed: {
    isEmpty: function() {
      return this.card === null;
    }
  },

  methods: {
    getCardImage: function(card) {
      return `http://www.clashapi.xyz/images/cards/${card.idName}.png`;
    },
    getPossibleEvents: function() {
      this.events = this.handleClickAction(this.card);
    },
    clearEvents: function() {
      this.events = [];
    }
  },

  directives: {
    ClickOutside
  }
};
</script>

<style scoped>
.card {
  border: none;
}

.card:hover {
  cursor: pointer;
}

.card,
.card > img {
  width: 100%;
  height: 10rem;
}

.card__elixir {
  position: absolute;
  top: 0;
  left: 0;
  color: white;
  background-color: #e960ec;
  border-radius: 50%;
  text-align: center;
  width: 35px;
  height: 35px;
  line-height: 33px;
}

.card.empty-card {
  border: 1px dashed grey;
}

.add-card-icon {
  margin: auto;
  color: grey;
  width: 50px;
  height: 100px;
}

.action-btns {
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 1;
}

.action-btns {
  width: 100%;
}

.action-btns > button {
  margin: 0;
}
</style>
