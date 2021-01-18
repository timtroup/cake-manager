import App from './App';
import {configure, mount, shallow} from "enzyme";
import Adapter from '@wojtekmaj/enzyme-adapter-react-17';
import axios from 'axios';
import {act} from "react-dom/test-utils";

jest.mock('axios');

const data = {
  data: [{
    title: "Lemon Cheesecake",
    description: "description",
    image: "http://www.villageinn.com/i/pies/profile/carrotcake_main1.jpg"
  }]
}

configure({adapter: new Adapter()});

describe("LoadImage test", () => {
  let wrapper;

  // clear all mocks
  afterEach(() => {
    jest.clearAllMocks();
  });

  test("renders with loading", () => {
    wrapper = shallow(<App/>);
    expect(wrapper.find("div").first().text()).toBe("...loading");
  });

  test('renders cake', async () => {

    await act(async () => {
      await axios.get.mockImplementationOnce(() => Promise.resolve(data));
      wrapper = mount(<App/>);
    });

    // check the render output
    wrapper.update();

    await expect(axios.get).toHaveBeenCalledWith("http://localhost:8080/cakes");

    await expect(axios.get).toHaveBeenCalledTimes(1);

    await expect(wrapper.find("img").props().src).toEqual("http://www.villageinn.com/i/pies/profile/carrotcake_main1.jpg");
  });
});
