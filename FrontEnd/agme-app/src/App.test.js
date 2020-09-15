import React from 'react';
import { render } from '@testing-library/react';
import { shallow, mount } from 'enzyme';
import App from './App';
import { MemoryRouter, Route } from 'react-router';
import Search from './components/search/Search';
import Dashboard from './components/Dashboard';
import CustomerProfile from './components/customerProfile/CustomerProfile';
import Register from './components/register/Register';
import Login from './components/login/Login';

test('renders site tagline', () => {
  const { getByText } = render(<App />);
  const linkElement = getByText(/AGME: Appointments Made Easy/i);
  expect(linkElement).toBeInTheDocument();
});


// test('search button should link to Search page', () => {
//   const wrapper = mount(
//     <Route initialEntries={[ '/search' ]}>
//       <App/>
//     </Route>
//   );
//   expect(wrapper.find(Search)).toHaveLength(1);
// });

// test('valid path should not redirect to 404', () => {
//   const wrapper = mount(
//     <MemoryRouter initialEntries={[ '/' ]}>
//       <App/>
//     </MemoryRouter>
//   );
//   expect(wrapper.find(LandingPage)).toHaveLength(1);
//   expect(wrapper.find(NotFoundPage)).toHaveLength(0);
// });



let pathMap = {};
describe('routes using array of routers', () => {
  beforeAll(() => {
    const component = shallow(<App/>);
    pathMap = component.find(Route).reduce((pathMap, route) => {
        const routeProps = route.props();
        pathMap[routeProps.path] = routeProps.component;
        return pathMap;
      }, {});
      console.log(pathMap)
  })
  it('should show Dashboard page for / route', () => {

    expect(pathMap['/']).toBe(Dashboard);
  })
  it('should show Search page for /search route', () => {
    expect(pathMap['/search']).toBe(Search);
  })
  it('should show CustomerProfile page for /customerProfile route', () => {
    expect(pathMap['/customerProfile']).toBe(CustomerProfile);
  })
  it('should show Register page for /register route', () => {
    expect(pathMap['/register']).toBe(Register);
  })
  it('should show Login page for /login route', () => {
    expect(pathMap['/login']).toBe(Login);
  })

})