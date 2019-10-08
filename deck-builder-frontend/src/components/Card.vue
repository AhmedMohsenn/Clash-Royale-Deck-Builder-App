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

    <b-button-group vertical
                    class="action-btns">
      <b-button v-if="events.includes('moreInfo')">Info</b-button>
      <b-button @click="$emit('remove-mode')"
                v-if="events.includes('remove')">Remove</b-button>
      <b-button v-if="events.includes('upgrade')">Upgrade</b-button>
    </b-button-group>
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
      return !this.card;
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

  watch: {
    events: {
      immediate: true,
      handler: function(newEvents) {
        const hasSelectEvent = newEvents.includes("select");
        const hasUseEvent = newEvents.includes("use");
        if (hasSelectEvent) this.$emit("select-mode");
        else if (hasUseEvent) this.$emit("use-card", this.card);
      }
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
  height: 100%;
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
  font-size: 0.9rem;
}
</style>
