import { useState } from 'react'
import { FaBeer } from 'react-icons/fa';
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Product from './components/Product';

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
     <div>
       <Product/>
     </div>
    </>
  )
}

export default App
