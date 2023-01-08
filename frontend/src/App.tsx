import {Routes, Route} from 'react-router-dom';
import Login from './Login/Login';
import MainPage from "./MainPage/MainPage";
import PersonalData from "./Data/PersonalData";
import UsersAdmin from "./Data/UsersAdmin";
import Initiatives from "./Initiative/Initiatives";
import InitiativeDetails from "./Initiative/initiativeDetails";

const App=()=>{
    return (
        <Routes>
            <Route path="/home" element={<MainPage/>}/>
            <Route path="/myData" element={<PersonalData/>}/>
            <Route path="/usersAdmin" element={<UsersAdmin/>}/>
            <Route path='/initiatives' element={<Initiatives/>}/>
            <Route path='/initiativeDetails' element={<InitiativeDetails/>}/>
            <Route path="/" element={<Login/>}/>
        </Routes>
    )
}
export default App