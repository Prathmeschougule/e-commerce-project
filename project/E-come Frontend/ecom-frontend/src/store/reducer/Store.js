import React from 'react'
import { configureStore } from '@reduxjs/toolkit'
import { ProductReducer } from './ProductReducer'

export const Store = configureStore({
    reducer :{
        
        products:ProductReducer,
    },
    preloadedState :{
    },
})

export default Store
