import {Routes, Route} from 'react-router-dom';
import Login from './Login/Login';
import MainPage from "./MainPage/MainPage";
import PersonalData from "./Data/PersonalData";
import UsersAdmin from "./Data/UsersAdmin";

const App=()=>{
    return (
        <Routes>
            <Route path="/home" element={<MainPage/>}/>
            <Route path="/myData" element={<PersonalData/>}/>
            <Route path="/usersAdmin" element={<UsersAdmin/>}/>
            <Route path="/" element={<Login/>}/>
        </Routes>
    )
}
export default App