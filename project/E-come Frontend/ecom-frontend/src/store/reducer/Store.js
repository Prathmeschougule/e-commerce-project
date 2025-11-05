import React from 'react'
import { configureStore } from '@reduxjs/toolkit'

export const Store = configureStore({
    reducer :{},
    preloadedState :{},
})

export default Store
